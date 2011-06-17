var Person = Class.extend({
    
    init: function(name) {
        this.name = name;
        this.currentLoc = [0,0];
        this.targetLoc = null;
        this.state = "Idle";
        this.speed = null;
        this.lastLocUpdate = 0.0;
    },

    display: function() {
        return this.name;
    },

    runTo: function(loc) {
        this.moveTo(loc, 8.0);
    },

    walkTo: function(loc) {
        this.moveTo(loc, 3.0);
    },

    moveTo: function(loc, speed) {
        this.targetLoc = loc;
        this.speed = speed;
        this.lastLocUpdate = gameTime;
    },

    location: function() {
        if(this.targetLoc != null)
        {
            if(this.speed == null || this.speed == 0.0)
            {
                this.currentLoc = this.targetLoc;
                this.targetLoc = null;
                this.speed = 0.0;
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
                    this.targetLoc = null;
                    this.speed = null;
                }
                else
                {
                    // the percentage of the total move to perform this update
                    var mp = dt / mt;
                    var dx = mp * mx;
                    var dy = mp * my;

                    // update the co-ordinates
                    this.currentLoc[0] += dx;
                    this.currentLoc[1] += dy;
                    this.lastLocUpdate = gameTime;
                }
            }
        }
        return this.currentLoc;
    }
});


var Fielder = Person.extend({
    init: function(name) {
        this._super(name)
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
    },
    all: function() {
        return [this.bowler, this.keeper, this.fielders[0], this.fielders[1], this.fielders[2], this.fielders[3], this.fielders[4], this.fielders[5], this.fielders[6], this.fielders[7], this.fielders[8], this.striker, this.nonStriker];
    },
    reset: function() {
        for(var i=0; i<this.fieldSetting.length; i++) {
            var fielder = new Fielder("Fielder " + i);
            fielder.currentLoc = this.fieldSetting[i];
            this.fielders[i] = fielder;
        }
    },
    sendFielderTo: function(loc) {
        this.fielders[0].runTo(loc);
    }
});


var WicketKeeper = Fielder.extend({
    init: function() {
        this._super("Wickie");
        this.currentLoc = [0,-10];
    }
});


var Bowler = Fielder.extend({
    init: function() {
        this._super("Bowler");
        this.currentLoc = [3,35];
    }
});


var BatsmanStriker = Person.extend({
    init: function() {
        this._super("Striker");
        this.currentLoc = [-1,1];
    }
});


var BatsmanNonStriker = Person.extend({
    init: function() {
        this._super("Non-striker");
        this.currentLoc = [-2,20];
    }
});
