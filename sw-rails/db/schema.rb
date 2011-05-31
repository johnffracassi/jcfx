# This file is auto-generated from the current state of the database. Instead
# of editing this file, please use the migrations feature of Active Record to
# incrementally modify your database, and then regenerate this schema definition.
#
# Note that this schema.rb definition is the authoritative source for your
# database schema. If you need to create the application database on another
# system, you should be using db:schema:load, not running all the migrations
# from scratch. The latter is a flawed and unsustainable approach (the more migrations
# you'll amass, the slower it'll run and the greater likelihood for issues).
#
# It's strongly recommended to check this file into your version control system.

ActiveRecord::Schema.define(:version => 20110530024950) do

  create_table "balls", :force => true do |t|
    t.integer  "ordinal"
    t.string   "score"
    t.integer  "runs"
    t.integer  "batsman_value"
    t.integer  "bowler_value"
    t.integer  "extra_type_id"
    t.integer  "dismissal_type_id"
    t.integer  "batsman_innings_id"
    t.integer  "over_id"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "bat_innings", :force => true do |t|
    t.integer  "player_id"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "batsman_innings", :force => true do |t|
    t.integer  "player_id"
    t.integer  "ordinal"
    t.integer  "match_id"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "clubs", :force => true do |t|
    t.string   "code"
    t.string   "name"
    t.string   "major_colour"
    t.string   "secondary_colour"
    t.string   "ternary_colour"
    t.string   "logo_small_url"
    t.string   "logo_medium_url"
    t.string   "logo_large_url"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "competition_genres", :force => true do |t|
    t.string   "code"
    t.string   "name"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "competitions", :force => true do |t|
    t.string   "name"
    t.integer  "competition_genre_id"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "dismissal_types", :force => true do |t|
    t.string   "code"
    t.string   "name"
    t.boolean  "counts_for_bowler"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "extra_types", :force => true do |t|
    t.string   "code"
    t.string   "name"
    t.integer  "value"
    t.boolean  "counts_for_bowler"
    t.boolean  "counts_for_batsman"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "grades", :force => true do |t|
    t.string   "name"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "matches", :force => true do |t|
    t.integer  "team_id"
    t.integer  "opposition_team_id"
    t.datetime "datetime"
    t.string   "round"
    t.text     "comments"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "memberships", :force => true do |t|
    t.integer  "player_id"
    t.integer  "club_id"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "nicknames", :force => true do |t|
    t.integer  "player_id"
    t.string   "name"
    t.integer  "weight"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "opposition_teams", :force => true do |t|
    t.string   "code"
    t.string   "name"
    t.text     "notes"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "overs", :force => true do |t|
    t.integer  "player_id"
    t.integer  "ordinal"
    t.integer  "match_id"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "player_aliases", :force => true do |t|
    t.integer  "player_id"
    t.string   "name"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "players", :force => true do |t|
    t.string   "code"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "profile_fields", :force => true do |t|
    t.string   "name"
    t.integer  "priority"
    t.integer  "profile_id"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "profiles", :force => true do |t|
    t.integer  "player_id"
    t.string   "email"
    t.string   "surname"
    t.string   "first_name"
    t.string   "origin_city"
    t.string   "origin_state"
    t.string   "origin_country"
    t.date     "dob"
    t.string   "blurb"
    t.text     "about"
    t.string   "picture_url"
    t.string   "flag_url"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "seasons", :force => true do |t|
    t.string   "code"
    t.string   "name"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "teams", :force => true do |t|
    t.integer  "club_id"
    t.integer  "season_id"
    t.integer  "competition_id"
    t.integer  "grade_id"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

end
