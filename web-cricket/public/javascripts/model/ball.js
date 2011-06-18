var BallModel = Class.extend({
   weight: 0.156,
   hardness: 1.0,
   width: 0.2,
   fillColour: "red",
   visible: true,
   currentLoc: null,
   path: null,
   pathStartTime: null,

   setPath: function(path) {
       this.path = path;
       this.pathStartTime = gameTime;
   },

   location: function() {
       if(this.path == null)
       {
           return this.currentLoc;
       }
       else
       {
           var pathTime = gameTime - this.pathStartTime;
           return this.path.location(pathTime);
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
            
            var sloc = convertWorldToScreen([wloc[0],wloc[1]]);
            g.fillStyle = "rgba(0,0,0,0.33)";
            g.fillRect(sloc[0],sloc[1], 2, 2);

            sloc = convertWorldToScreen(wloc);
            g.fillStyle = this.model.fillColour;
            g.fillRect(sloc[0],sloc[1], 2, 2);
        }
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
    var locx = parseInt(document.getElementById("ballX").value);
    var locy = parseInt(document.getElementById("ballY").value);
    var locz = parseInt(document.getElementById("ballZ").value);
    var vx = parseInt(document.getElementById("vx").value);
    var vy = parseInt(document.getElementById("vy").value);
    var vz = parseInt(document.getElementById("vz").value);
    var loc = [locx,locy,locz];
    ballModel.currentLoc = loc;
    ballModel.setPath(calculatePath(loc, [vx, vy, vz]));
}

function calculatePath(iloc, vmatrix) {
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
            vx = calculateNewVelocity(vx, 0.7);
            vy = calculateNewVelocity(vy, 0.7);
            vz = calculateNewVelocity(-vz, 0.3);
        }

        var newLoc = [lastLoc[0]+dx, lastLoc[1]+dy, lastLoc[2]+dz];
        lastLoc = newLoc;
        path.push(newLoc);
    }
    return new ProjectilePath(path);
}

function calculateNewVelocity(v, eff)
{
    var e = (0.5 * ballModel.weight * v*v) * eff;
    var newv = Math.sqrt(2 * e / ballModel.weight);
    return v > 0 ? newv : -newv;
}