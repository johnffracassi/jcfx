class CreateOvers < ActiveRecord::Migration
  def self.up
    create_table :overs do |t|
      t.integer :player_id
      t.integer :ordinal
      t.integer :match_id
      t.timestamps
    end

    add_index :overs, :player_id
    add_index :overs, :match_id

  end

  def self.down
    drop_table :overs
  end
end
