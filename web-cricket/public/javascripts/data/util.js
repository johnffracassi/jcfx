var xscale = 7.6;
var yscale = -4.5;
var perspective = -0.0015;
var zscale = xscale;

function convertScreenToWorld(sloc)
{
    return [(sloc[0] - screenOrigin[0]) / xscale, (sloc[1] - screenOrigin[1]) / yscale];
}

function convertWorldToScreen(wloc)
{
    if(wloc.length == 2)
    {
        var xscale2 = 1.0 + (perspective * wloc[1]);
        var x = (wloc[0] * xscale) * xscale2 + screenOrigin[0];
        var y = wloc[1] * yscale + screenOrigin[1];
        return [x, y];
    }
    else if(wloc.length == 3)
    {
        var p2d = convertWorldToScreen([wloc[0], wloc[1]]);
        return [p2d[0], p2d[1] - (wloc[2]*zscale)];
    }
}

function distance2d(p1, p2)
{
    var dx = p1[0] - p2[0];
    var dy = p1[1] - p2[1];
    return Math.sqrt(dx*dx + dy*dy);
}

function angle(dx, dy)
{
    if(dx >= 0.0 && dy < 0.0) // quadrant 1
    {
        return toDegrees(Math.atan(dx / -dy));
    }
    else if(dx >= 0.0 && dy >= 0.0) // quadrant 2
    {
        return toDegrees(Math.atan(dy / dx) + (Math.PI / 2));
    }
    else if(dx < 0.0 && dy >= 0.0) // quadrant 3
    {
        return toDegrees(Math.atan(-dx / dy) + Math.PI);
    }
    else // quadrant 4
    {
        return toDegrees(Math.atan(-dy / -dx) + 3*Math.PI / 2);
    }
}

function toDegrees(radians)
{
    return radians * 180 / Math.PI;
}

function toRadians(degrees)
{
    return Math.PI * (degrees / 180);
}