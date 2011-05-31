namespace :sw do

  task :profiles => :environment do
    Dir.glob("data/profile/*.profile").each do |file|
      import_profile file
    end
  end

  def import_profile(file)
    File.open(file, "r") do |infile|
      puts "Importing profile from #{file}"

      player_code = file.split(".")[0].split("/")[-1]
      player = Player.find_or_create_by_code(player_code)
      profile = Profile.new :player => player

      while (line = infile.gets)
        if /^fullname/i =~ line
          fullname = val(line)
          profile.first_name = fullname.split.first
          profile.surname = fullname.split.last
        elsif /^origin/i =~ line
          origin = val(line)
          profile.origin_city = origin.split(",")[-3]
          profile.origin_state = origin.split(",")[-2]
          profile.origin_country = origin.split(",")[-1]
        elsif /^nicknames/i =~ line
          nicknames = val(line).split(",")
          nicknames.each do |name|
            Nickname.create :player => player, :name => name
          end
        elsif /^profile/i =~ line
          profile.about = val(line)
        elsif /^flag/i =~ line
          profile.flag_url = 'http://toot-toot.appspot.com/images/' + val(line)
        end
      end

      profile.save

    end
  end

  def val(str)
    /.*=\s*(.*)\s*$/i.match(str)[1]
  end
end