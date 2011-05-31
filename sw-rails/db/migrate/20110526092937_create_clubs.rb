class CreateClubs < ActiveRecord::Migration
  def self.up
    create_table :clubs do |t|
      t.string :code
      t.string :name
      t.string :major_colour
      t.string :secondary_colour
      t.string :ternary_colour
      t.string :logo_small_url
      t.string :logo_medium_url
      t.string :logo_large_url
      t.timestamps
    end
  end

  def self.down
    drop_table :clubs
  end
end
