var imgSpritesReady = false;
var imgSprites = new Image();
imgSprites.src = "images/sprites.png";
imgSprites.onload = function() {
    imgSpritesReady = true;
}

var spriteHeight = 19;
var spriteWidth = 19;
var animFPS = 20;
var animFPSMult = targetFPS / animFPS;

// animations should be done at 25fps
var anim = new Array();
anim['BatNonStriker_Idle'] = [[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,5],[0,5],[0,5],[0,2],[0,2],[0,5],[0,5],[0,5],[0,5],[0,5],[0,2]];
anim['BatStriker_Idle'] = [[0,1],[0,1],[0,1],[0,1],[0,1],[0,1],[0,1],[0,1],[0,1],[0,1],[0,1],[0,1],[0,1],[0,1],[0,1],[0,1],[0,1],[0,0],[0,0],[0,0],[0,1],[0,1],[0,1],[0,1],[0,1],[0,1],[0,0],[0,0],[0,0],[0,1],[0,1],[0,1],[0,0],[0,0],[0,0]];
anim['Fielder_Running'] = [[1,1], [1,1], [1,1], [1,1], [1,2], [1,2], [1,2], [1,2]];
anim['Fielder_Idle'] = [[1, 0]];
anim['Person_Idle'] = [[1, 0]];
anim['Bowler_Running'] = anim['Fielder_Running'];
anim['Bowler_Idle'] = anim['Fielder_Idle'];

function drawSprite(g, spriteRow, spriteCol, sloc)
{
    var dx = sloc[0];
    var dy = sloc[1];
    var dx1 = (dx - spriteWidth / 2 + 1);
    var dy1 = (dy - spriteHeight) + 3;

    if(imgSpritesReady == true)
    {
        var sx = (spriteWidth + 1) * spriteCol + 1;
        var sy = (spriteHeight + 1) * spriteRow + 1;
        g.drawImage(imgSprites, sx, sy, spriteWidth, spriteHeight, dx1, dy1, spriteWidth, spriteHeight);
    }
}

function drawAnimation(g, key, sloc)
{
    if(typeof(anim[key]) === 'undefined')
    {
        g.fillText(key, sloc[0], sloc[1]);
    }
    else
    {
        var frameIdx = 0;
        if(actualFPS > 0)
        {
            frameIdx = Math.floor(frameCounter / (actualFPS / animFPS));
        }

        var frame = frameIdx % anim[key].length;
        var spriteRow = anim[key][frame][0];
        var spriteCol = anim[key][frame][1];
        drawSprite(g, spriteRow, spriteCol, sloc);
    }
}

