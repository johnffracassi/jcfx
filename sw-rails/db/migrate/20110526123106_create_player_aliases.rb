class CreatePlayerAliases < ActiveRecord::Migration
  def self.up
    create_table :player_aliases do |t|
      t.integer :player_id
      t.string :name
      t.timestamps
    end
  end

  def self.down
    drop_table :player_aliases
  end
end
