var Person = Class.extend({
    
    init: function(name) {
        this.name = name;
        this.currentLoc = [0,0];
        this.targetLoc = null;
        this.state = "Idle";
        this.speed = null;
        this.lastLocUpdate = 0.0;
        this.lastStateUpdate = 0.0;
        this.spriteClass = "Person";
    },

    display: function() {
        return this.name;
    },

    runTo: function(loc) {
        this.moveTo(loc, 8.0);
        this.setState("Running");
    },

    walkTo: function(loc) {
        this.moveTo(loc, 3.0);
        this.setState("Walking");
    },

    moveTo: function(loc, speed) {
        if(speed == 0)
        {
            this.targetLoc = null;
            this.currentLoc = loc;
            this.lastLocUpdate = gameTime;
            this.setState("Idle");
        }
        else
        {
            this.targetLoc = loc;
            this.speed = speed;
            this.lastLocUpdate = gameTime;
        }
    },

    setState: function(state) {
        this.state = state;
        this.lastStateUpdate = gameTime;
    },

    setLoc: function(loc) {
        this.currentLoc = loc;
        this.lastLocUpdate = gameTime;
    },

    location: function() {
        if(this.targetLoc != null)
        {
            if(this.speed == null || this.speed == 0.0)
            {
                this.targetLoc = null;
                this.speed = 0.0;
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
                var mt = md / this.speed;

                // time since last location update
                var dt = gameTime - this.lastLocUpdate;

                // the player has arrived if move time is less than the delta time
                if(mt <= dt)
                {
                    this.moveTo(this.currentLoc, 0);
                    // TODO do callback here!!!
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

    animKey: function() {
        return this.spriteClass + "_" + this.state;
    }
});


var Fielder = Person.extend({
    init: function(name) {
        this._super(name)
        this.spriteClass = "Fielder";
    }
});


var PersonController = Class.extend({
    init: function() {
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
            this.fielders[i] = new Fielder("Fielder " + i);;
        }
    },
    all: function() {
        return [this.umpire, this.umpireSquareLeg, this.bowler, this.keeper, this.fielders[0], this.fielders[1], this.fielders[2], this.fielders[3], this.fielders[4], this.fielders[5], this.fielders[6], this.fielders[7], this.fielders[8], this.striker, this.nonStriker];
    },
    reset: function() {
        for(var i=0; i<this.fieldSetting.length; i++)
        {
            this.fielders[i].moveTo(this.fieldSetting[i], 0);
            this.bowler.moveTo([2, 33], 0);
            this.keeper.moveTo([0, -10], 0);
            this.striker.moveTo([-1, 1], 0);
            this.nonStriker.moveTo([-2, 21], 0);
            this.umpire.moveTo([-0.1, 24], 0);
            this.umpireSquareLeg.moveTo([-24, 0], 0);
        }
    },
    sendFielderTo: function(loc) {
        this.fielders[0].runTo(loc);
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