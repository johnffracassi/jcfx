class CreateBalls < ActiveRecord::Migration
  def self.up
    create_table :balls do |t|
      t.integer :ordinal
      t.string  :score
      t.integer :runs
      t.integer :batsman_value
      t.integer :bowler_value
      t.integer :extra_type_id
      t.integer :dismissal_type_id
      t.integer :batsman_innings_id
      t.integer :over_id
      t.timestamps
    end
  end

  def self.down
    drop_table :balls
  end
end
