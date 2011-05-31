genre = CompetitionGenre.create! :code => 'IC', :name => "Indoor Cricket"

comp = Competition.create! :name => "Play On Sports", :competition_genre => genre

sw = Club.create! :name => "Steamboat Willies", :code => "SW"

gradeS = Grade.create! :name => "Superleague"
gradeA = Grade.create! :name => "A"
gradeB = Grade.create! :name => "B"

(2006..2011).each do |year|
  [["sum","Summer"],["win","Winter"]].each do |season|
    Season.create! :name => "#{season[1]} #{year}", :code => "#{year}#{season[0]}"
  end
  (1..4).each do |quarter|
    th = Season.create! :name => "Q#{quarter} #{year} (Th)", :code => "#{year}q#{quarter}th"
    mo = Season.create! :name => "Q#{quarter} #{year} (Mo)", :code => "#{year}q#{quarter}mo"
  end
end

Team.create! :club => sw, :competition => comp, :season => Season.find_by_code('2006q3th'), :grade => gradeA
Team.create! :club => sw, :competition => comp, :season => Season.find_by_code('2006q4th'), :grade => gradeB
Team.create! :club => sw, :competition => comp, :season => Season.find_by_code('2007q1th'), :grade => gradeB
Team.create! :club => sw, :competition => comp, :season => Season.find_by_code('2007q2th'), :grade => gradeA
Team.create! :club => sw, :competition => comp, :season => Season.find_by_code('2007q2th'), :grade => gradeB
Team.create! :club => sw, :competition => comp, :season => Season.find_by_code('2008q1th'), :grade => gradeB
Team.create! :club => sw, :competition => comp, :season => Season.find_by_code('2008q4mo'), :grade => gradeB
Team.create! :club => sw, :competition => comp, :season => Season.find_by_code('2008q4th'), :grade => gradeB
Team.create! :club => sw, :competition => comp, :season => Season.find_by_code('2009q1th'), :grade => gradeB
Team.create! :club => sw, :competition => comp, :season => Season.find_by_code('2009q2th'), :grade => gradeB
Team.create! :club => sw, :competition => comp, :season => Season.find_by_code('2009q4th'), :grade => gradeB
Team.create! :club => sw, :competition => comp, :season => Season.find_by_code('2010q1th'), :grade => gradeA
Team.create! :club => sw, :competition => comp, :season => Season.find_by_code('2010q2th'), :grade => gradeS
Team.create! :club => sw, :competition => comp, :season => Season.find_by_code('2010q4th'), :grade => gradeB
Team.create! :club => sw, :competition => comp, :season => Season.find_by_code('2011q1th'), :grade => gradeB
Team.create! :club => sw, :competition => comp, :season => Season.find_by_code('2011q2th'), :grade => gradeB

ExtraType.create! :code => 'W', :name => 'Wide', :counts_for_batsman => true, :counts_for_bowler => true, :value => 2
ExtraType.create! :code => 'LS', :name => 'Wide', :counts_for_batsman => true, :counts_for_bowler => true, :value => 2
ExtraType.create! :code => 'NB', :name => 'No-Ball', :counts_for_batsman => true, :counts_for_bowler => true, :value => 2
DismissalType.create! :code => 'B', :name => 'Bowled', :counts_for_bowler => true
DismissalType.create! :code => 'C', :name => 'Caught', :counts_for_bowler => true
DismissalType.create! :code => 'S', :name => 'Stumped', :counts_for_bowler => true
DismissalType.create! :code => 'R', :name => 'Run Out', :counts_for_bowler => true
DismissalType.create! :code => 'M', :name => 'Mankad', :counts_for_bowler => true
