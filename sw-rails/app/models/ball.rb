class Ball < ActiveRecord::Base

  belongs_to :batsman_innings
  belongs_to :over
  belongs_to :dismissal_type
  belongs_to :extra_type

  def category
    return '7+' if batsman_value >= 7
    return '5' if batsman_value >= 5
    return '3-4' if batsman_value >= 3
    return '1' if batsman_value == 1
    return 'Dot' if batsman_value == 0
    return 'Extra' unless extra_type.nil?
    return 'Wicket' unless dismissal_type.nil?
    return '2' if batsman_value == 2
    return score
  end

end
