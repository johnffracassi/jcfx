class Match < ActiveRecord::Base

  belongs_to :team
  belongs_to :opposition_team
  has_many :batsman_inningss
  has_many :overs

end
