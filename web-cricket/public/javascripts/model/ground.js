var GroundModel = Class.extend({

    straightLength: 75,
    squareLength: 65,
    sections: 64,
    fillColour: "rgb(30, 140, 0)",
    bounce: 0.2,
    roll: 1.0,
    poly: null,
    fieldingCirclePoly: null,

    init: function()
    {
        this.poly = calculateEllipse(0, 10, this.squareLength, this.straightLength, 0, this.sections);
        this.fieldingCirclePoly = calculateEllipse(0, 10, 26, 40, 0, 36);
    },

    boundContains: function(pt)
    {
        if(pt[0] > (this.straightLength) + 10 || pt[0] < -(this.straightLength - 10))
        {
            return false;
        }

        if(pt[1] > this.squareLength || pt[1] < -this.squareLength)
        {
            return false;
        }

        return true;
    },

    contains: function(pt)
    {
        if(!this.boundContains(pt))
        {
            return false;
        }

        var i, j, c = false;
        for (i = 0, j = this.poly.length - 1; i < this.poly.length; j = i++)
        {
            if (((this.poly[i][1] > pt[1]) != (this.poly[j][1] > pt[1])) && (pt[0] < (this.poly[j][0] - this.poly[i][0]) * (pt[1] - this.poly[i][1]) / (this.poly[j][1] - this.poly[i][1]) + this.poly[i][0]))
            {
                c = !c;
            }
        }

        return c;
    }
});

var GroundRenderer = Class.extend({
    render: function(model, g, loc)
    {
        var pts = model.poly;
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

        // 30m circle
        var pts = model.fieldingCirclePoly;
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
    var beta = -angle * (Math.PI / 180);
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
