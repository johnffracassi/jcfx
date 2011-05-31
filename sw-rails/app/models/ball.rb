class Ball < ActiveRecord::Base

  belongs_to :batsman_innings
  belongs_to :over
  belongs_to :dismissal_type
  belongs_to :extra_type

end
