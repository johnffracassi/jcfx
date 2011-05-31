class BatsmanInnings < ActiveRecord::Base

  belongs_to :match
  belongs_to :player
  has_many :balls

  def score
    runs = 0
    balls.each do |ball|
      runs += ball.batsman_value
    end
    runs
  end

  def bf
    balls.count
  end

end
