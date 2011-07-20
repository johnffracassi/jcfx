var DEFAULT_PROXIMITY_TOLERANCE = 0.6;
var PATH_RESOLUTION = 30;
var PATH_TOTAL_TIME = 15; // in seconds
var PATH_TIMESTEP = 1.0 / PATH_RESOLUTION;
var KEEPER_TAKE_HEIGHT = 0.75;
var GRAVITY = -9.8;
var FIELDER_REACH_HEIGHT = 2.0;

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

   setProjectilePath: function(path)
   {
       this.path = path;
       this.pathStartTime = gameTime;
       this.proximityTriggers = new Array();
   },

   setProjectile: function(loc, vmatrix)
   {
       ballModel.currentLoc = loc;
       ballModel.setProjectilePath(calculateProjectilePath(loc, vmatrix));
   },

   updateLocation: function()
   {
       if(this.path == null)
       {
           if(this.currentLoc == null)
           {
               this.currentLoc = personController.umpire.location();
           }
       }
       else
       {
           this.currentLoc = this.path.location(this.getPathTime());

           if(this.proximityTriggers.length > 0)
           {
               for(var idx=0; idx<this.proximityTriggers.length; idx++)
               {
                   if(this.proximityTriggers[idx].shouldTrigger(this.currentLoc))
                   {
                       this.proximityTriggers[idx].fire();
                   }
               }
           }

       }

       return this.currentLoc;
   },

   addProximityTrigger: function(trigger, callback, tolerance)
   {
       this.proximityTriggers.push(new ProximityTrigger(trigger, callback, tolerance));
   },

   getPathTime: function()
   {
       return gameTime - this.pathStartTime;
   },

   hasBounced: function()
   {
       if(this.path.firstBounceIndex == null)
       {
           return false;
       }
       else
       {
           var currentPathIdx = this.path.indexForTime(this.getPathTime());
           return (currentPathIdx >= this.path.firstBounceIndex);
       }
   }
});


var ProximityTrigger = Class.extend({

    tolerance: null,
    trigger: null,
    callback: null,
    fired: false,
    location: null,

    init: function(trigger, callback, tolerance)
    {
        this.callback = callback;
        this.tolerance = (tolerance == null) ? DEFAULT_PROXIMITY_TOLERANCE : tolerance;

        if(typeof trigger != 'function')
        {
            this.trigger = function()
            {
                this.location = trigger;
                var distFromTrigger = distance2d(trigger, ballModel.currentLoc);
                return distFromTrigger < this.tolerance;
            }
        }
        else
        {
            this.trigger = trigger;
        }
    },

    fire: function()
    {
        this.fired = true;
        this.callback();
    },

    shouldTrigger: function(location)
    {
        if(this.fired == true)
        {
            return false;
        }

        return (this.trigger() == true);
    }
});


var BallRenderer = Class.extend({
    init: function(model)
    {
        this.model = model;
    },
    
    render: function()
    {
        if(this.model.visible == true && this.model.currentLoc != null)
        {
            var wloc = this.model.currentLoc;

            // shadow
            var sloc = convertWorldToScreen([wloc[0],wloc[1]]);
            g.fillStyle = "rgba(0,0,0,0.33)";
            g.fillRect(sloc[0],sloc[1], 2, 2);

            // ball
            sloc = convertWorldToScreen(wloc);
            g.fillStyle = this.model.fillColour;
            g.fillRect(sloc[0],sloc[1], 2, 2);
        }

        if(this.model.currentLoc == null)
        {
            console.log("fuck it");
        }
        else
        {
            var y = 14;
            g.fillStyle = 'black';
            var msg = "Ball Location: [" + (0|this.model.currentLoc[0]) + "," + (0|this.model.currentLoc[1]) + "," + (0|this.model.currentLoc[2]) + "]";
            msg += " (" + groundModel.contains(this.model.currentLoc) + ")";
            g.fillText(msg , 250, y += 20);
            g.fillText("Proximity Triggers: " + this.model.proximityTriggers.length, 250, y += 20);
            for(var idx=0; idx < this.model.proximityTriggers.length; idx++)
            {
                var d = -1;
                if(this.model.proximityTriggers[idx].location != null)
                {
                    d = distance2d(this.model.proximityTriggers[idx].location, ballModel.currentLoc);
                }
                var str = this.model.proximityTriggers[idx].shouldTrigger(ballModel.currentLoc) + " / " + this.model.proximityTriggers[idx].tolerance + " / " + (0|d);
                g.fillText("proximityTriggers[" + idx + "]: " + str, 250, y += 20);
            }
        }
    }
});


var ProjectilePath = Class.extend({

   firstBounceIndex: null,

   init: function(points, firstBounceIdx)
   {
       this.points = points;
       this.firstBounceIndex = firstBounceIdx;
   },
   indexForTime: function(pathTime)
   {
       return Math.max(0, pathTime * PATH_RESOLUTION);
   },
   velocityMatrix: function(pathTime)
   {
       var pointIdx = this.indexForTime(pathTime);
       return this.velocityMatrixForIndex(pointIdx);
   },
   velocityMatrixForIndex: function(idx)
   {
       if(idx == 0)
       {
           idx = 1;
       }

       var p0 = this.locationForIndex(idx);
       var p1 = this.locationForIndex(idx-1);
       var dx = (p0[0] - p1[0]) * PATH_RESOLUTION;
       var dy = (p0[1] - p1[1]) * PATH_RESOLUTION;
       var dz = (p0[2] - p1[2]) * PATH_RESOLUTION;

       return [dx,dy,dz];
   },
   location: function(pathTime)
   {
       var pointIdx = this.indexForTime(pathTime);
       return this.locationForIndex(pointIdx);
   },
   locationForIndex: function(pointIdx)
   {
       if(pointIdx >= 0 && pointIdx < this.points.length -1)
       {
           var actualIdx = (0|pointIdx);
           var interpolation = (pointIdx - actualIdx);
           var p1 = this.points[actualIdx];
           var p2 = this.points[actualIdx + 1];
           return interpolate(p1, p2, interpolation);
       }
       else if(pointIdx < 0)
       {
           return this.points[0];
       }
       else
       {
           return this.points[this.points.length - 1];
       }
   },
   duration: function()
   {
       return this.points * PATH_RESOLUTION;
   },
   terminateAt: function(t)
   {
       this.points = this.points.slice(0, Math.ceil(t * PATH_RESOLUTION));
   },
   startLoc: function()
   {
       return this.points[0];
   },
   endLoc: function()
   {
       return this.points[this.points.length - 1];
   },
   getLocForFirstYLessThan: function(y)
   {
       for(var i=0; i<this.points.length; i++)
       {
           var pt = this.points[i];
           if(pt[1] < y)
           {
               return pt;
           }
       }
       return null;
   }
});



////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

var ballModel = new BallModel();
var ballRenderer = new BallRenderer(ballModel);

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
    ballModel.setProjectilePath(calculateProjectilePath(loc, [vx, vy, vz]));
}

function calculateProjectilePath(iloc, vmatrix)
{
    console.log("====| new path |====================");

    var path = new Array();
    path.push(iloc);
    var lastLoc = [iloc[0],iloc[1],iloc[2]];
    var vx = vmatrix[0];
    var vy = vmatrix[1];
    var vz = vmatrix[2];

    var firstBounceIdx = null;
    for(var idx = 0; idx < PATH_TOTAL_TIME * PATH_RESOLUTION; idx++)
    {
        var dx = PATH_TIMESTEP * vx;
        var dy = PATH_TIMESTEP * vy;
        var dz = PATH_TIMESTEP * vz;
        vz += -9.8 * PATH_TIMESTEP;
        var newz = lastLoc[2] + dz;

        // bounce
        if(newz < 0 && vz < 0)
        {
            // TODO efficiencies should be based upon the angle of incidence
            vx = applyEnergyChangeAfterBounce(vx, 0.7);
            vy = applyEnergyChangeAfterBounce(vy, 0.7);
            vz = applyEnergyChangeAfterBounce(-vz, 0.3);

            if(firstBounceIdx == null)
            {
                console.log("first bounce #" + idx);
                firstBounceIdx = idx;
            }
        }

        var newLoc = [lastLoc[0]+dx, lastLoc[1]+dy, lastLoc[2]+dz];
        lastLoc = newLoc;
        path.push(newLoc);
    }

    return new ProjectilePath(path, firstBounceIdx);
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
    for(var idx = 0; idx < projectilePath.points.length; idx++)
    {
        var ballLoc = projectilePath.points[idx];

        // don't look unless ball is below 2m high
        if(ballLoc[2] < FIELDER_REACH_HEIGHT)
        {
            var distanceFromPersonToBallLoc = distance2d(personLoc, ballLoc);
            var timeForPersonToRunToBallLoc = distanceFromPersonToBallLoc / personModel.runSpeed;
            var ballTime = idx / PATH_RESOLUTION;

            if(timeForPersonToRunToBallLoc <= ballTime)
            {
                var result = new Array();
                result['personTime'] = timeForPersonToRunToBallLoc;
                result['ballTime'] = ballTime;
                result['location'] = projectilePath.points[idx];
                result['person'] = personModel;
                return result;
            }
        }
    }

    var result = new Array();
    result['ballTime'] = projectilePath.duration();
    result['location'] = projectilePath.endLoc();
    result['personTime'] = distance2d(projectilePath.endLoc(), personLoc) / personModel.runSpeed;
    result['person'] = personModel;
    return result;
}


// TODO need a better way to approximate the trajectory, need to take in to consideration the fielders throwSpeed
function approximateTrajectory(origin, target, speed)
{
    var dx = target[0] - origin[0];
    var dy = target[1] - origin[1];
    var d  = distance2d(origin, target);

    var flightTime = d / speed;

    // solve for vz=0 halfway through the trajectory
    var uz = -GRAVITY * (flightTime / 2);

    var pointCount = (flightTime / PATH_TIMESTEP) + 1;

    var points = new Array();
    for(var idx=0; idx<pointCount; idx++)
    {
        var time = idx * PATH_TIMESTEP;
        points.push([origin[0] + time / flightTime * dx, origin[1] + time / flightTime * dy, KEEPER_TAKE_HEIGHT + uz * time + 0.5 * GRAVITY * time*time]);
    }

    return new ProjectilePath(points);
}