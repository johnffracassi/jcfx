var sSkinColour         = [255,128,64];
var sPrimaryColour      = [255,0,0];
var sSecondaryColour    = [0,0,255];
var tSkinColour         = [255,128,64];
var tPrimaryColour      = [255,255,255];
var tSecondaryColour    = [0,0,128];
var cWhite = [255,255,255];
var cBlack = [0,0,0];

var imgSpritesReady = false;
var imgSprites = new Image();
imgSprites.src = "images/sprites.png";
imgSprites.onload = function()
{
    var canvas = document.createElement('canvas');
    var canvasContext = canvas.getContext('2d');
    var imgW = imgSprites.width;
    var imgH = imgSprites.height;
    canvas.width = imgW;
    canvas.height = imgH;
    canvasContext.drawImage(imgSprites, 0, 0);

    var imgPixels = canvasContext.getImageData(0, 0, imgW, imgH);

    for(var y = 0; y < imgPixels.height; y++)
    {
         for(var x = 0; x < imgPixels.width; x++)
         {
             var i = (y * 4) * imgPixels.width + x * 4;
             var pix = [imgPixels.data[i], imgPixels.data[i + 1], imgPixels.data[i + 2]];

             if(colourEq(pix, sPrimaryColour))
             {
                 colourSet(imgPixels, i, tPrimaryColour);
             }
             else if(colourEq(pix, sSecondaryColour))
             {
                 colourSet(imgPixels, i, tSecondaryColour);
             }
             else if(colourEq(pix, sSkinColour))
             {
                 colourSet(imgPixels, i, tSkinColour);
             }
         }
    }
    canvasContext.putImageData(imgPixels, 0, 0, 0, 0, imgPixels.width, imgPixels.height);
    imgSprites.src = canvas.toDataURL();
    imgSpritesReady = true;
}

function colourSet(imgPixels, offset, col)
{
    imgPixels.data[offset + 0] = col[0];
    imgPixels.data[offset + 1] = col[1];
    imgPixels.data[offset + 2] = col[2];
}

function colourEq(c1, c2)
{
    return (c1[0] == c2[0] && c1[1] == c2[1] && c1[2] == c2[2]);
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
