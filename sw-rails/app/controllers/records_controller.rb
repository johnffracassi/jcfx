class RecordsController < ApplicationController

  def index
  end

  def most_runs_career
    balls_by_batsman = Ball.all.group_by{|b|b.batsman}
  end

end
