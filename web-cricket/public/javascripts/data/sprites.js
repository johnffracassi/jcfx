var imgSpritesReady = false;
var imgSprites = new Image();
imgSprites.src = "images/sprites.png";
imgSprites.onload = function() {
    imgSpritesReady = true;
}

var spriteHeight = 19, spriteWidth = 19;

var anim = new Array();
anim['BatIdle'] = generateAnimBatIdle();
function generateAnimBatIdle() { return [[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,2],[0,5],[0,5],[0,5],[0,2],[0,2],[0,5],[0,5],[0,5],[0,5],[0,5],[0,2]]; }

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
    var spriteRow = anim[key][frameCounter % anim[key].length][0];
    var spriteCol = anim[key][frameCounter % anim[key].length][1];
    drawSprite(g, spriteRow, spriteCol, sloc);
}

