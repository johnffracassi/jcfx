var PitchModel = Class.extend({
   length: 20,
   width: 2.7,
   crease: 1.2,
   fillColour: "rgba(255, 222, 192, 0.33)",
   strokeStyle: "rgba(255, 255, 255, 0.33)",
   efficiency: 0.6,
   spin: 0.5,
   suprisiness: 0.1
});

var PitchRenderer = Class.extend({
    render: function(model,g,loc) {
        var bottomLeft = [loc[0]-model.width/2,loc[1] - 3];
        var topRight = [loc[0]+model.width/2,loc[1]+model.length + 3];
        var pt0 = convertWorldToScreen(bottomLeft);
        var pt1 = convertWorldToScreen([bottomLeft[0], topRight[1]]);
        var pt2 = convertWorldToScreen(topRight);
        var pt3 = convertWorldToScreen([topRight[0], bottomLeft[1]]);

        g.fillStyle = model.fillColour;
        g.beginPath();
        g.moveTo(pt0[0], pt0[1]);
        g.lineTo(pt1[0], pt1[1]);
        g.lineTo(pt2[0], pt2[1]);
        g.lineTo(pt3[0], pt3[1]);
        g.closePath();
        g.fill();

        g.strokeStyle = model.strokeStyle;
        screenLine(g, [-4,1.5],[4,1.5]);
        screenLine(g, [-2,0],[2,0]);
        screenLine(g, [-4,model.length],[4,model.length]);
        screenLine(g, [-2,model.length + 1.5],[2,model.length + 1.5]);
    }
});

var StumpsModel = Class.extend({
   width: 0.3,
   height: 0.8,
   strokeStyle: "rgb(255,192,0)",
   state: "Idle"
});

var StumpsRenderer = Class.extend({
    render: function(model,g,loc) {
        g.strokeStyle = model.strokeStyle;
        g.lineWidth = 0.75;
        screenLine(g, [loc[0],loc[1],0], [loc[0],loc[1],model.height]);
        screenLine(g, [loc[0]+model.width/2,loc[1],0], [loc[0]+model.width/2,loc[1],model.height]);
        screenLine(g, [loc[0]-model.width/2,loc[1],0], [loc[0]-model.width/2,loc[1],model.height]);
    }
});

function screenLine(g, start, end)
{
    var sloc1 = convertWorldToScreen(start);
    var sloc2 = convertWorldToScreen(end);
    g.beginPath();
    g.moveTo(sloc1[0], sloc1[1]);
    g.lineTo(sloc2[0], sloc2[1]);
    g.stroke();
}

