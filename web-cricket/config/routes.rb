WebCricket::Application.routes.draw do

  root :to => 'main#index'

  get 'test' => 'main#test'
  get 'sprites' => 'sprites#index'

end
