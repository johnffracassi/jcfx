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
    if(typeof p1 === 'undefined' || typeof p2 === 'undefined')
    {
        console.log("Can't calculate distance between " + p1 + " and " + p2);
    }
    else if(p1 == null || p2 == null)
    {
        console.log("One of the points is null, no idea what to do? " + p1 + " and " + p2);
    }
    else
    {
        var dx = p1[0] - p2[0];
        var dy = p1[1] - p2[1];
        return Math.sqrt(dx*dx + dy*dy);
    }
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

function interpolate(p1, p2, percentage)
{
    var result = new Array();
    result.push(p1[0] + ((p2[0] - p1[0]) * percentage));
    result.push(p1[1] + ((p2[1] - p1[1]) * percentage));

    if(p1.length == 3 && p2.length == 3)
    {
        result.push(p1[2] + ((p2[2] - p1[2]) * percentage));
    }

    return result;
}

function fuzzyLoc(loc, range)
{
    var newx = loc[0] - range + (2 * Math.random() * range);
    var newy = loc[1] - range + (2 * Math.random() * range);
    return [newx, newy, 0];
}