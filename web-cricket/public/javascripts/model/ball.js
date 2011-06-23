var BallModel = Class.extend({
   weight: 0.156,
   hardness: 1.0,
   width: 0.2,
   fillColour: "red",
   visible: true,
   currentLoc: null,
   path: null,
   pathStartTime: null,
   proximityTriggers: new Array(),

   reset: function()
   {
       this.visible = false;
       this.currentLoc = null;
       this.path = null;
       this.pathStartTime = null;
       this.proximityTriggers = new Array();
   },

   setPath: function(path)
   {
       this.path = path;
       this.pathStartTime = gameTime;
       this.proximityTriggers = new Array();
   },

   setProjectile: function(loc, vmatrix)
   {
       ballModel.currentLoc = loc;
       ballModel.setPath(calculatePath(loc, vmatrix));
   },

   location: function()
   {
       if(this.path == null)
       {
           return this.currentLoc;
       }
       else
       {
           var pathTime = gameTime - this.pathStartTime;
           var loc = this.path.location(pathTime);

           if(this.proximityTriggers.length > 0)
           {
               var ptIdx = 0;
               for(ptIdx=0; ptIdx<this.proximityTriggers.length; ptIdx++)
               {
                   var trigger = this.proximityTriggers[ptIdx];
                   if(trigger.shouldTrigger(loc))
                   {
                       trigger.callback();
                       trigger.fired = true;
                   }
               }
           }

           this.currentLoc = loc;
           return loc;
       }
   },

   addProximityTrigger: function(trigger, callback)
   {
       this.proximityTriggers.push(new ProximityTrigger(trigger, callback, null));
   }
});


var ProximityTrigger = Class.extend({
    tolerance: null,
    trigger: null,
    callback: null,
    fired: false,

    init: function(trigger, callback, tolerance)
    {
        this.trigger = trigger;
        this.callback = callback;
        this.tolerance = tolerance||0.6;
    },
    shouldTrigger: function(location)
    {
        if(this.fired == true)
        {
            return false;
        }

        if(typeof this.trigger == "function")
        {
            return (this.trigger() == true);
        }
        else if(this.trigger instanceof Array)
        {
            var d = distance2d(this.trigger, location);
            return d < this.tolerance;
        }
    }
});


var BallRenderer = Class.extend({
    init: function(model)
    {
        this.model = model;
    },
    render: function() {
        if(this.model.visible == true && this.model.currentLoc != null)
        {
            var wloc = this.model.location();

            // shadow
            var sloc = convertWorldToScreen([wloc[0],wloc[1]]);
            g.fillStyle = "rgba(0,0,0,0.33)";
            g.fillRect(sloc[0],sloc[1], 2, 2);

            // ball
            sloc = convertWorldToScreen(wloc);
            g.fillStyle = this.model.fillColour;
            g.fillRect(sloc[0],sloc[1], 2, 2);
        }
    }
});


var ProjectilePath = Class.extend({
   init: function(points) {
       this.points = points;
   },
   location: function(pathTime) {
       var idx = Math.floor(pathTime * pathResolution);
       idx = Math.min(idx, this.points.length-1);
       idx = Math.max(idx, 0);
       return this.points[idx];
   },
   pathDuration: function() {
       return this.points * pathResolution;
   },
   terminateAt: function(t) {
       this.points = this.points.slice(0, Math.floor(t * pathResolution));
   }
});


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

var ballModel = new BallModel();
var ballRenderer = new BallRenderer(ballModel);

var pathResolution = 50;
var pathTimeStep = 1.0 / pathResolution;

function projectBall()
{
    var locx = parseFloat(document.getElementById("ballX").value);
    var locy = parseFloat(document.getElementById("ballY").value);
    var locz = parseFloat(document.getElementById("ballZ").value);
    var vx = parseFloat(document.getElementById("vx").value);
    var vy = parseFloat(document.getElementById("vy").value);
    var vz = parseFloat(document.getElementById("vz").value);
    var loc = [locx,locy,locz];
    ballModel.currentLoc = loc;
    ballModel.setPath(calculatePath(loc, [vx, vy, vz]));
}

function calculatePath(iloc, vmatrix)
{
    var path = new Array();
    path.push(iloc);
    var lastLoc = [iloc[0],iloc[1],iloc[2]];
    var vx = vmatrix[0];
    var vy = vmatrix[1];
    var vz = vmatrix[2];

    var idx = 0;
    for(;idx<500;idx++)
    {
        var dx = pathTimeStep * vx;
        var dy = pathTimeStep * vy;
        var dz = pathTimeStep * vz;
        vz += -9.8 * pathTimeStep;
        var newz = lastLoc[2] + dz;

        // bounce
        if(newz < 0 && vz < 0)
        {
            vx = applyEnergyChangeAfterBounce(vx, 0.7);
            vy = applyEnergyChangeAfterBounce(vy, 0.7);
            vz = applyEnergyChangeAfterBounce(-vz, 0.3);
        }

        var newLoc = [lastLoc[0]+dx, lastLoc[1]+dy, lastLoc[2]+dz];
        lastLoc = newLoc;
        path.push(newLoc);
    }
    return new ProjectilePath(path);
}

function applyEnergyChangeAfterBounce(v, eff)
{
    var e = (0.5 * ballModel.weight * v*v) * eff;
    var newv = Math.sqrt(2 * e / ballModel.weight);
    return v > 0 ? newv : -newv;
}

function fastestTimeToPath(personModel, projectilePath)
{
    var personLoc = personModel.location();

    var bestTimeThusfarForPerson = 999999;
    var bestTimeThusfarForBall = 999999;

    var idx = 0;
    for(;idx<projectilePath.points.length; idx++)
    {
        var ballLoc = projectilePath.points[idx];

        // don't look unless ball is below 2m high
        if(ballLoc[2] < 2.0)
        {
            var distanceFromPersonToBallLoc = distance2d(personLoc, ballLoc);
            var timeForPersonToRunToBallLoc = distanceFromPersonToBallLoc / personModel.runSpeed;
            if(timeForPersonToRunToBallLoc < bestTimeThusfarForPerson)
            {
                bestTimeThusfarForPerson = timeForPersonToRunToBallLoc;
                bestTimeThusfarForBall = idx / pathResolution;
            }
        }
    }

    var result = new Array();
    result['personTime'] = bestTimeThusfarForPerson;
    result['ballTime'] = bestTimeThusfarForBall;
    result['location'] = projectilePath.location(bestTimeThusfarForBall);
    result['person'] = personModel;
    return result;
}

var keeperTakeHeight = 0.75;
var gravity = -9.8;

function approximateTrajectory(origin, target, speed)
{
    var dx = target[0] - origin[0];
    var dy = target[1] - origin[1];
    var d  = distance2d(origin, target);

    var flightTime = d / speed;

    // solve for vz=0 halfway through the trajectory
    var uz = -gravity * (flightTime / 2);

    var pointCount = (flightTime / pathTimeStep) + 1;

    var points = new Array();
    for(var idx=0; idx<pointCount; idx++)
    {
        var time = idx * pathTimeStep;
        points.push([origin[0] + time / flightTime * dx, origin[1] + time / flightTime * dy, keeperTakeHeight + uz * time + 0.5 * gravity * time*time]);
    }

    return new ProjectilePath(points);
}