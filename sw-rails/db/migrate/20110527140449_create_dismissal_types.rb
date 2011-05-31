class CreateDismissalTypes < ActiveRecord::Migration
  def self.up
    create_table :dismissal_types do |t|
      t.string :code
      t.string :name
      t.boolean :counts_for_bowler
      t.timestamps
    end
  end

  def self.down
    drop_table :dismissal_types
  end
end
