class Player < ActiveRecord::Base

  has_many :player_aliases
  has_many :nicknames
  has_one :profile

  def display
    profile.nil? ? code : profile.display
  end

  def name
    profile.nil? ? code : profile.name
  end

end
