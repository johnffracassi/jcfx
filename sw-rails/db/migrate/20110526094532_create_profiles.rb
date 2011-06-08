class CreateProfiles < ActiveRecord::Migration
  def self.up
    create_table :profiles do |t|
      t.integer :player_id
      t.string :email
      t.string :surname
      t.string :first_name
      t.string :origin_city
      t.string :origin_state
      t.string :origin_country
      t.date :dob
      t.string :blurb
      t.text :about
      t.string :picture_url
      t.string :flag_url

      t.timestamps
    end

    add_index :profiles, :player_id
  end

  def self.down
    drop_table :profiles
  end
end
