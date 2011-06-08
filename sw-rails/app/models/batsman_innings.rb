class BatsmanInnings < ActiveRecord::Base

  belongs_to :match
  belongs_to :player
  has_many :balls

  def self.find_all_by_filter(order, filter)

    limit = filter[:limit] || 50
    order_sql = (order == 'desc') ? 'order by runs desc, bf asc' : 'order by runs asc, bf desc'
    tables = {'batsman_innings' => 'bi', 'balls' => 'b'}
    clause = ""
    params = []

    if filter[:season_id]
      tables['seasons'] = 's'
      tables['matches'] = 'm'
      tables['teams'] = 't'
      clause += "m.team_id = t.id and bi.match_id = m.id and t.season_id = s.id and s.id = #{filter[:season_id]} and "
    end

    if filter[:player_id]
      clause += "bi.player_id in (#{filter[:player_id]})"
    end

    tables_str = ""
    tables.each{|x,y|tables_str += "#{x} #{y}," }
    tables_str = tables_str[0..-2]

    sql = "select bi.*, sum(b.batsman_value) runs, count(b.batsman_value) bf " +
            "from #{tables_str} " +
            "where #{clause} bi.id = b.batsman_innings_id " +
            "group by bi.id " +
            "#{order_sql} " +
            "limit #{limit}"

    connection.execute(sql)
  end

  def score
    runs = 0
    balls.each do |ball|
      runs += ball.batsman_value
    end
    runs
  end

  def bf
    balls.count
  end

end
