class CreateMatches < ActiveRecord::Migration
  def self.up
    create_table :matches do |t|
      t.integer :team_id
      t.integer :opposition_team_id
      t.datetime :datetime
      t.string :round
      t.text :comments
      t.timestamps
    end
  end

  def self.down
    drop_table :matches
  end
end
