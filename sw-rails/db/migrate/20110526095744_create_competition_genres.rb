class CreateCompetitionGenres < ActiveRecord::Migration
  def self.up
    create_table :competition_genres do |t|
      t.string :code
      t.string :name
      t.timestamps
    end
  end

  def self.down
    drop_table :competition_genres
  end
end
