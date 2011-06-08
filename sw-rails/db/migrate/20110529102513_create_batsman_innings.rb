class CreateBatsmanInnings < ActiveRecord::Migration
  def self.up
    create_table :batsman_innings do |t|
      t.integer :player_id
      t.integer :ordinal
      t.integer :match_id
      t.timestamps
    end

    add_index :batsman_innings, :player_id
    add_index :batsman_innings, :match_id
  end

  def self.down
    drop_table :batsman_innings
  end
end
