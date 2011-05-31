namespace :sw do

  task :games => :environment do
    Dir.glob("data/game/*.game").each do |file|
      import_game file
    end
  end

  def import_game(file)
    File.open(file, "r") do |infile|

      puts "Importing game data #{file}"

      match = Match.new
      date = ""
      num = 0;

      while (line = infile.gets)

        num = num + 1
        puts "line #{num}) #{line}"

        if /^season/i =~ line
          season_id = val(line)
          match.team = Team.find(season_id)
        elsif /^us/i =~ line
          us = val(line)
        elsif /^them/i =~ line
          them = val(line)
          match.opposition_team = OppositionTeam.find_by_name(them.strip) || OppositionTeam.create!(:name => them.strip)
          match.save

          puts "=> #{match.team.club.name} vs #{match.opposition_team.name}"
          puts " > #{match.team.season.name}, rd #{match.round}"
          puts " > #{match.datetime}"
        elsif /^round/i =~ line
          match.round = val(line)
        elsif /^time/i =~ line
          time = val(line)
          match.datetime = DateTime.parse(date + " " + time)
        elsif /^date/i =~ line
          date = val(line)
        elsif num >= 8 and num < 16
          batting_line(match, num - 7, line)
        elsif num >= 16
          bowling_line(match, num - 16, line)
        end
      end

    end
  end

  def batting_line(match, ordinal, str)

    if str.blank?
      return nil
    end

    array = str.split(%r{\s})
    batsman = Player.find_or_create_by_code(:code => array.shift.upcase)
    inns = BatsmanInnings.create :ordinal => ordinal, :player => batsman, :match => match

    array.each_index do |ordinal|
      new_ball = ball(array[ordinal])
      new_ball.ordinal = ordinal+1
      new_ball.batsman_innings = inns
      new_ball.save unless new_ball.score.blank?
    end
  end

  def bowling_line(match, ordinal, str)

    if str.blank?
      return nil
    end

    array = str.split(%r{\s})
    bowler = Player.find_or_create_by_code(array.shift.upcase)
    over = Over.create :ordinal => ordinal, :player => bowler, :match => match

    array.each_index do |ordinal|
      new_ball = ball(array[ordinal])
      new_ball.ordinal = ordinal+1
      new_ball.over = over
      new_ball.save unless new_ball.score.blank?
    end
  end

  def ball score

    ball = Ball.new :score => score.upcase

    if(score =~ /^[0-9]$/)
      ball.runs = score.to_i
      ball.batsman_value = score.to_i
      ball.bowler_value = score.to_i
    elsif(score =~ /^(w|nb|ls)([0-9]?)$/i)
      val = score.match(/^(w|nb|ls)([0-9]?)$/i)
      ball.extra_type    = ExtraType.find_by_code(val[1].upcase)
      ball.runs          = val[2].to_i
      ball.batsman_value = 2 + ball.runs
      ball.bowler_value  = 2 + ball.runs
    elsif(score =~ /^[cbrslm]$/i)
      ball.dismissal_type = DismissalType.find_by_code(score.upcase)
      ball.runs          = -5
      ball.batsman_value = -5
      ball.bowler_value  = -5
    end

    return ball
  end

  def val(str)
    /.*=\s*(.*)\s*$/i.match(str)[1]
  end
end