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

