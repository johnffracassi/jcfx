require 'test_helper'

class CompetitionGenresControllerTest < ActionController::TestCase
  setup do
    @competition_genre = competition_genres(:one)
  end

  test "should get index" do
    get :index
    assert_response :success
    assert_not_nil assigns(:competition_genres)
  end

  test "should get new" do
    get :new
    assert_response :success
  end

  test "should create competition_genre" do
    assert_difference('CompetitionGenre.count') do
      post :create, :competition_genre => @competition_genre.attributes
    end

    assert_redirected_to competition_genre_path(assigns(:competition_genre))
  end

  test "should show competition_genre" do
    get :show, :id => @competition_genre.to_param
    assert_response :success
  end

  test "should get edit" do
    get :edit, :id => @competition_genre.to_param
    assert_response :success
  end

  test "should update competition_genre" do
    put :update, :id => @competition_genre.to_param, :competition_genre => @competition_genre.attributes
    assert_redirected_to competition_genre_path(assigns(:competition_genre))
  end

  test "should destroy competition_genre" do
    assert_difference('CompetitionGenre.count', -1) do
      delete :destroy, :id => @competition_genre.to_param
    end

    assert_redirected_to competition_genres_path
  end
end
