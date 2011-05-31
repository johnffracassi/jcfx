SwRails::Application.routes.draw do

  root :to => "home#index"

  get "/profiles/:id/career" => "profiles#career"
  get "/profiles/:id/batting" => "profiles#batting"
  get "/profiles/:id/bowling" => "profiles#bowling"

  get "/admin" => "common#admin"

  resources :extra_types
  resources :dismissal_types
  resources :matches
  resources :opposition_teams
  resources :player_aliases
  resources :grades
  resources :competition_genres
  resources :teams
  resources :competitions
  resources :seasons
  resources :nicknames
  resources :profiles
  resources :memberships
  resources :clubs
  resources :players
  
end
