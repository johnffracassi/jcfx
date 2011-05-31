module MatchesHelper

def bat_worm match
    acc = 0
    worm = []

    match.batsman_inningss.each do |inns|
        inns.balls.each do |ball|
            worm << [(inns.ordinal+1)/2, ball.ordinal, ball.batsman_value]
        end
    end

    dataset = []

    worm.sort{|x,y| x[0]*27+x[1] <=> y[0]*27+y[1]}.each do |dp|
       acc += dp[2].to_i
       dataset << [(dp[0]-1)*27 + dp[1], acc]
    end

    dataset
  end

def bowl_worm match
    acc = 0
    worm = []

    match.overs.each do |over|
        over.balls.each do |ball|
            acc += ball.bowler_value
            worm << [over_start_idx(over.ordinal) + ball.ordinal, acc]
        end
    end

    worm
  end

  def over_start_idx(idx)
    [0,6,12,18, 27,33,39,45, 54,60,66,72, 81,87,93,99][idx]
  end

end
