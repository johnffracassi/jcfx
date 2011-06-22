var GroundModel = Class.extend({
    straightLength: 75,
    squareLength: 65,
    sections: 64,
    fillColour: "rgb(30, 140, 0)",
    bounce: 0.2,
    roll: 1.0
});

var GroundRenderer = Class.extend({
    render: function(model,g,loc)
    {
        var pts = calculateEllipse(0, 10, model.squareLength, model.straightLength, 0, model.sections);
        g.fillStyle = model.fillColour;
        g.beginPath();
        var pt = convertWorldToScreen([pts[0][0],pts[0][1]]);
        g.moveTo(pt[0],pt[1]);
        var i=0;
        for(i=1; i<pts.length; i++)
        {
            pt = convertWorldToScreen([pts[i][0],pts[i][1]]);
            g.lineTo(pt[0],pt[1]);
        }
        g.closePath();
        g.fill();
        g.lineWidth = 2;
        g.strokeStyle = "black";
        g.stroke();

        var pts = calculateEllipse(0, 10, 26, 40, 0, 36);
        g.beginPath();
        var pt = convertWorldToScreen([pts[0][0],pts[0][1]]);
        g.moveTo(pt[0],pt[1]);
        var i=0;
        for(i=1; i<pts.length; i++)
        {
            pt = convertWorldToScreen([pts[i][0],pts[i][1]]);
            g.lineTo(pt[0],pt[1]);
        }
        g.closePath();
        g.strokeStyle = "rgba(255,255,255,0.075)";
        g.stroke();
    }
});


var imgGroundReady = false;
var imgGround = new Image();
imgGround.src = "images/ground.jpg";
imgGround.onload = function() { imgGroundReady = true; }
function renderGroundBitmap()
{
    if(imgGroundReady == true)
    {
        g.drawImage(imgGround, 0, 0, 800, 600);
    }
}


function calculateEllipse(x, y, a, b, angle, steps)
{
  var points = [];

  // Angle is given by Degree Value
  var beta = -angle * (Math.PI / 180); //(Math.PI/180) converts Degree Value into Radians
  var sinbeta = Math.sin(beta);
  var cosbeta = Math.cos(beta);

  for (var i = 0; i < 360; i += 360 / steps)
  {
    var alpha = i * (Math.PI / 180) ;
    var sinalpha = Math.sin(alpha);
    var cosalpha = Math.cos(alpha);

    var X = x + (a * cosalpha * cosbeta - b * sinalpha * sinbeta);
    var Y = y + (a * cosalpha * sinbeta + b * sinalpha * cosbeta);

    points.push([X, Y]);
   }

  return points;
}