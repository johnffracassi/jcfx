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

