class Team < ActiveRecord::Base

  belongs_to :club
  belongs_to :season
  belongs_to :competition
  belongs_to :grade

end
