class CreateBatInnings < ActiveRecord::Migration
  def self.up
    create_table :bat_innings do |t|
      t.integer :player_id
      t.timestamps
    end
  end

  def self.down
    drop_table :bat_innings
  end
end
