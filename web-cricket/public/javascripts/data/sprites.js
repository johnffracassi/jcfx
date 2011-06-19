var imgSpritesReady = false;
var imgSprites = new Image();
imgSprites.src = "images/sprites.png";
imgSprites.onload = function() {
    imgSpritesReady = true;
}

var spriteHeight = 19;
var spriteWidth = 19;
var animFPS = 20;

var Animation = Class.extend({
    init: function(repeats, frames, completeFrame) {
        this.frames = frames;
        this.repeats = repeats;
        this.completeFrame = completeFrame;
    },
    sprite: function(startTime) {
        var t = gameTime - startTime;
        var absFrameIdx = Math.floor(t * animFPS);
        var repetition = Math.floor(absFrameIdx / this.frames.length);
        var frameIdx = absFrameIdx % this.frames.length;
        if(repetition >= this.repeats && this.repeats > 0)
        {
            return this.completeFrame;
        }
        else
        {
            return this.frames[frameIdx];
        }
    }
});

var SingleFrame = Class.extend({
    init: function(frame) {
        this.frame = frame;
    },
    sprite: function(startTime) {
        return this.frame;
    }
});

function drawSprite(g, spriteRow, spriteCol, sloc)
{
    var dx = sloc[0];
    var dy = sloc[1];
    var dx1 = (dx - spriteWidth / 2) - 1;
    var dy1 = (dy - spriteHeight) + 3;

    if(imgSpritesReady == true)
    {
        var sx = (spriteWidth + 1) * spriteCol + 1;
        var sy = (spriteHeight + 1) * spriteRow + 1;
        g.drawImage(imgSprites, sx, sy, spriteWidth, spriteHeight, dx1, dy1, spriteWidth, spriteHeight);
    }
}

function drawAnimation(key, sloc, startTime)
{
    if(typeof(anim[key]) === 'undefined')
    {
        g.fillText(key, sloc[0], sloc[1]);
    }
    else
    {
        var sprite = anim[key].sprite(startTime);

        if(typeof(sprite) === 'undefined')
        {
            g.fillText(key + "/" + startTime, sloc[0], sloc[1]);
        }
        else
        {
            drawSprite(g, sprite[0], sprite[1], sloc);
        }
    }
}



// animations should be done at 20fps
var anim = new Array();

anim['Person_Idle']         = new SingleFrame([1, 0]);

anim['BatStriker_Idle']     = new Animation(0, [[0,1],[0,1],[0,1],[0,1],[0,1],[0,1],[0,1],[0,1],[0,1],[0,1],[0,1],[0,1],[0,1],[0,1],[0,1],[0,1],[0,1],[0,0],[0,0],[0,0],[0,1],[0,1],[0,1],[0,1],[0,1],[0,1],[0,0],[0,0],[0,0],[0,1],[0,1],[0,1],[0,0],[0,0],[0,0]], [0,11]);
anim['BatNonStriker_Idle']  = new Animation(0, [[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,5],[0,5],[0,5],[0,2],[0,2],[0,5],[0,5],[0,5],[0,5],[0,5],[0,2]], [0,11]);

anim['Fielder_Running']     = new Animation(0, [[1,1], [1,1], [1,2], [1,2]]);
anim['Fielder_Idle']        = new SingleFrame([1, 0]);

anim['Bowler_Idle']         = anim['Fielder_Idle'];
anim['Bowler_Running']      = anim['Fielder_Running'];
anim['Bowler_Action']       = new Animation(1, [[3,0],[3,0],[3,1],[3,1],[3,2],[3,2],[3,3],[3,4],[3,5],[1,2]], [1,0]);

anim['Umpire_Idle']         = new SingleFrame([2,0]);
anim['Umpire_SignalFour']   = new Animation(5, [[2,2],[2,2],[2,2],[2,2],[2,3],[2,3],[2,3],[2,3]], [2,0]);
anim['Umpire_SignalSix']    = new Animation(40, [[2,1]], [2,0]);
anim['Umpire_SignalWide']   = new Animation(40, [[2,5]], [2,0]);
anim['Umpire_SignalNoBall'] = new Animation(40, [[2,3]], [2,0]);
anim['Umpire_SignalOut']    = new Animation(40, [[2,9]], [2,0]);
anim['Umpire_SignalBye']    = new Animation(40, [[2,8]], [2,0]);
anim['Umpire_SignalLegBye'] = new Animation(5, [[2,7],[2,7],[2,7],[2,7],[2,6],[2,6],[2,6],[2,6]], [2,0]);

