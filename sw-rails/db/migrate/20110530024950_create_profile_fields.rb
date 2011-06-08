class CreateProfileFields < ActiveRecord::Migration
  def self.up
    create_table :profile_fields do |t|
      t.string :name
      t.integer :priority
      t.integer :profile_id
      t.timestamps
    end

    add_index :balls, :batsman_innings_id
    add_index :balls, :over_id
  end

  def self.down
    drop_table :profile_fields
    remove_index :balls, :batsman_innings_id
    remove_index :balls, :over_id
  end
end
