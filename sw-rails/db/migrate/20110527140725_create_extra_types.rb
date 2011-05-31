class CreateExtraTypes < ActiveRecord::Migration
  def self.up
    create_table :extra_types do |t|
      t.string :code
      t.string :name
      t.integer :value
      t.boolean :counts_for_bowler
      t.boolean :counts_for_batsman
      t.timestamps
    end
  end

  def self.down
    drop_table :extra_types
  end
end
