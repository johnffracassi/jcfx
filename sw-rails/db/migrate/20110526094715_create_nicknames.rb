class CreateNicknames < ActiveRecord::Migration
  def self.up
    create_table :nicknames do |t|
      t.integer :player_id
      t.string :name
      t.integer :weight
      t.timestamps
    end

    add_index :nicknames, :player_id
  end

  def self.down
    drop_table :nicknames
  end
end
