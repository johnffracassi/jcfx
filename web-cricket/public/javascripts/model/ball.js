var BallModel = Class.extend({
   weight: 0.156,
   hardness: 1.0,
   width: 0.2,
   fillColour: "red",
   visible: true,
   currentLoc: null,
   path: null,
   pathStartTime: null,
   proximityTrigger: null,

   setPath: function(path) {
       this.path = path;
       this.pathStartTime = gameTime;
   },

   setProjectile: function(loc, dir) {
       ballModel.currentLoc = loc;
       ballModel.setPath(calculatePath(loc, dir));
   },

   location: function() {
       if(this.path == null)
       {
           return this.currentLoc;
       }
       else
       {
           var pathTime = gameTime - this.pathStartTime;
           var loc = this.path.location(pathTime);

           if(this.proximityPoint != null && this.proximityTrigger != null)
           {
               var d = distance2d(this.proximityPoint, loc);
               if(d < 0.6)
               {
                   this.proximityTrigger();
                   this.proximityTrigger = null;
               }
           }

           return loc;
       }
   },

   addProximityTrigger: function(point, callback) {
       this.proximityTrigger = callback;
       this.proximityPoint = point;
       console.log("setting proximity point to => " + point);
   }
});

var BallRenderer = Class.extend({
    init: function(model) {
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
    for(i=1; i<500; i++)
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
    for(i=0; i<projectilePath.points.length; i++)
    {
        var ballLoc = projectilePath.points[i];

        // don't look unless ball is below 2m high
        if(ballLoc[2] < 2.0)
        {
            var distanceFromPersonToBallLoc = distance2d(personLoc, ballLoc);
            var timeForPersonToRunToBallLoc = distanceFromPersonToBallLoc / personModel.walkSpeed;
            if(timeForPersonToRunToBallLoc < bestTimeThusfarForPerson)
            {
                bestTimeThusfarForPerson = timeForPersonToRunToBallLoc;
                bestTimeThusfarForBall = i / pathResolution;
            }
        }
    }

    var result = new Array();
    result['personTime'] = bestTimeThusfarForPerson;
    result['ballTime'] = bestTimeThusfarForBall;
    result['location'] = projectilePath.location(bestTimeThusfarForBall);
    return result;
}