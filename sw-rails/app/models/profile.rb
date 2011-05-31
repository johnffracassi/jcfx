class Profile < ActiveRecord::Base

  belongs_to :player
  has_many :profile_fields

  def display
    "<img src='#{flag_url}' />#{name}".html_safe
  end

  def name
    first_name + " " + surname
  end

  def origin
    origin_city.to_s + ", " + origin_state.to_s + ", " + origin_country.to_s
  end

  def age

    return "N/A" if dob.nil?

    age = Date.today.year - dob.year
    age -= 1 if Date.today < dob + age.years

    return age
    
  end

end
