class Over < ActiveRecord::Base

  belongs_to :match
  belongs_to :player
  has_many :balls


  def score
    runs = 0
    balls.each do |ball|
      runs += ball.bowler_value
    end
    runs
  end

  def bf
    balls.count
  end

end
