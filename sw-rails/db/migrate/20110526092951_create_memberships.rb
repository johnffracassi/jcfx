class CreateMemberships < ActiveRecord::Migration
  def self.up
    create_table :memberships do |t|
      t.integer :player_id
      t.integer :club_id
      t.timestamps
    end
  end

  def self.down
    drop_table :memberships
  end
end
