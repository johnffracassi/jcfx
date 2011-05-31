class CreateOppositionTeams < ActiveRecord::Migration
  def self.up
    create_table :opposition_teams do |t|
      t.string :code
      t.string :name
      t.text :notes
      t.timestamps
    end
  end

  def self.down
    drop_table :opposition_teams
  end
end
