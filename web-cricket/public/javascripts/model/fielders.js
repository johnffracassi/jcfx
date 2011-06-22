var Person = Class.extend({
    
    init: function(name)
    {
        this.name = name;
        this.runSpeed = 7.0;
        this.walkSpeed = 2.0;

        this.currentLoc = [0,0];
        this.targetLoc = null;
        this.state = "Idle";
        this.currentSpeed = null;
        this.lastLocUpdate = 0.0;
        this.lastStateUpdate = 0.0;
        this.spriteClass = "Person";
        this.moveCompleteCallback = null;
    },

    display: function()
    {
        return this.name;
    },

    runTo: function(loc, callback)
    {
        this.moveTo(loc, this.runSpeed, callback);
        this.setState("Running");
    },

    walkTo: function(loc, callback)
    {
        this.moveTo(loc, this.walkSpeed, callback);
        this.setState("Walking");
    },

    moveTo: function(loc, speed, callback)
    {
        if(speed == 0)
        {
            this.targetLoc = null;
            this.setLoc(loc);
            this.setState("Idle");
        }
        else
        {
            this.targetLoc = loc;
            this.currentSpeed = speed;
            this.lastLocUpdate = gameTime;
            this.moveCompleteCallback = callback;
        }
    },

    setState: function(state)
    {
        this.state = state;
        this.lastStateUpdate = gameTime;
    },

    setLoc: function(loc)
    {
        this.currentLoc = loc;
        this.lastLocUpdate = gameTime;
    },

    location: function()
    {
        if(this.targetLoc != null)
        {
            if(this.currentSpeed == null || this.currentSpeed == 0.0)
            {
                this.targetLoc = null;
                this.currentSpeed = 0.0;
                this.setLoc(this.targetLoc);
                this.setState("Idle");
            }
            else
            {
                // work out the total move distance
                var mx = this.targetLoc[0] - this.currentLoc[0];
                var my = this.targetLoc[1] - this.currentLoc[1];
                var md = Math.sqrt(mx*mx + my*my);

                // total move time
                var mt = md / this.currentSpeed;

                // time since last location update
                var dt = gameTime - this.lastLocUpdate;

                // the player has arrived if move time is less than the delta time
                if(mt <= dt)
                {
                    this.moveTo(this.currentLoc, 0);
                    if(this.moveCompleteCallback != null)
                    {
                        this.moveCompleteCallback(this);
                        this.moveCompleteCallback = null;
                    }
                }
                else
                {
                    // the percentage of the total move to perform this update
                    var mp = dt / mt;
                    var dx = mp * mx;
                    var dy = mp * my;

                    // update the co-ordinates
                    var loc = [this.currentLoc[0] + dx, this.currentLoc[1] + dy];
                    this.setLoc(loc);
                }
            }
        }
        return this.currentLoc;
    },

    animKey: function()
    {
        return this.spriteClass + "_" + this.state;
    }
});


var Fielder = Person.extend({
    init: function(name)
    {
        this._super(name)
        this.spriteClass = "Fielder";
    }
});


var PersonController = Class.extend({
    init: function()
    {
        this.fieldSetting = fieldSettings['Standard'];
        this.fielders = new Array(9);
        this.bowler = new Bowler();
        this.keeper = new WicketKeeper();
        this.striker = new BatsmanStriker();
        this.nonStriker = new BatsmanNonStriker();
        this.umpire = new Umpire();
        this.umpireSquareLeg = new Umpire();

        for(var i=0; i<this.fieldSetting.length; i++)
        {
            this.fielders[i] = new Fielder("Fielder " + i);
        }
    },

    all: function()
    {
        return [this.umpire, this.umpireSquareLeg, this.bowler, this.keeper, this.fielders[0], this.fielders[1], this.fielders[2], this.fielders[3], this.fielders[4], this.fielders[5], this.fielders[6], this.fielders[7], this.fielders[8], this.striker, this.nonStriker];
    },

    fielderables: function()
    {
        return [this.bowler, this.keeper, this.fielders[0], this.fielders[1], this.fielders[2], this.fielders[3], this.fielders[4], this.fielders[5], this.fielders[6], this.fielders[7], this.fielders[8]];
    },

    reset: function()
    {
        for(var idx=0; idx<this.fieldSetting.length; idx++)
        {
            this.fielders[idx].moveTo(this.fieldSetting[idx], 0);
            this.bowler.moveTo([2, 33], 0);
            this.keeper.moveTo([0, -10], 0);
            this.striker.moveTo([-0.2, 1.25], 0);
            this.nonStriker.moveTo([-2, 21], 0);
            this.umpire.moveTo([-0.1, 24], 0);
            this.umpireSquareLeg.moveTo([-24, 0], 0);
        }
    },

    sendFielderTo: function(loc)
    {
        var candidates = this.fielderables();
        var closestDistance = Infinity;
        var closestFielder = candidates[0];

        // find the closest fielder to the point
        for(var idx=0; idx<candidates.length; idx++)
        {
            var distance = distance2d(candidates[idx].currentLoc, loc);
            if(distance < closestDistance)
            {
                closestDistance = distance;
                closestFielder = candidates[idx];
            }
        }

        closestFielder.runTo(loc);
    },

    sendFielderAfterBall: function()
    {
        var candidates = this.fielderables();
        var closestIntersection = null;

        // find the closest fielder to the ball path
        for(var idx = 0; idx<candidates.length; idx++)
        {
            var intersection = fastestTimeToPath(candidates[idx], ballModel.path);

            if(closestIntersection == null || (intersection['personTime'] < closestIntersection['personTime']))
            {
                closestIntersection = intersection;
            }
        }

        closestIntersection['person'].runTo(closestIntersection['location']);
    }
});


var WicketKeeper = Fielder.extend({
    init: function() {
        this._super("Wickie");
    }
});


var Bowler = Fielder.extend({
    init: function() {
        this._super("Bowler");
        this.spriteClass = "Bowler";
    }
});


var BatsmanStriker = Person.extend({
    init: function() {
        this._super("Striker");
        this.spriteClass = "BatStriker";
    }
});


var BatsmanNonStriker = Person.extend({
    init: function() {
        this._super("Non-striker");
        this.spriteClass = "BatNonStriker";
    }
});

var Umpire = Person.extend({
   init: function() {
       this._super("Umpire");
       this.spriteClass = "Umpire";
   }
});