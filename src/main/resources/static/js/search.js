app.controller("food-modal", function ($scope, $uibModalInstance, opkpService, foodId) {
	opkpService.getFood(foodId).then(function (data) {
		console.log(data);
		
		$scope.data = data.food.objects[0];
		$scope.data.ingredients = data.ingredients.objects;
	});

	$scope.ok = function () {
		$uibModalInstance.close();
	};

	$scope.cancel = function () {
		$uibModalInstance.dismiss('cancel');
	};
});

app.controller('recipe-modal', function ($scope, $uibModalInstance, opkpService, recipeId, $modal) {
	opkpService.getRecipe(recipeId).then(function (data) {
		console.log(data);
		
		$scope.data = {
			recipe: data.recipe.objects[0],
			ingredients: data.ingredients.objects
		};
	});

	$scope.ok = function () {
		$uibModalInstance.close();
	};

	$scope.cancel = function () {
		$uibModalInstance.dismiss('cancel');
	};

	$scope.onIngredientClick = function (ingredient) {
		var foodId = ingredient["FOODID"];

		//$uibModalInstance.dismiss('cancel');
		var modal = $modal.open({
			templateUrl: "templates/food-modal.html",
			controller: "food-modal",
			size: "lg",
			resolve: {
				foodId: function () {
					return foodId;
				}
			}
		});
	};
});

app.controller("search", function ($scope, $uibModal, opkpService, $http) {
	$scope.pageSize = 20;
	$scope.searchNodes = [{
		title: "Food",
		name: "fir_food"
	}, {
			title: "Recipe",
			name: "fir_recipe"
		}];
	$scope.response = {};
	$scope.searchPromise = null;

	$scope.search = function () {
		$scope.currentPage = 1;
		search();
	}

	$scope.getSearches = function (val) {
		return opkpService.search("fir_food", val.split(/ +/), 0, 5).then(function (res) {
			return res.objects.map(function (food) {
				return food.ORIGFDNM;
			});
		});
	}

	$scope.onPageChanged = function (nodeIndex, page) {
		$scope.currentPage = page;
		window.scrollTo(0, 0);

		search();
	}

	$scope.selectSearchTab = function (nodeIndex) {
		$scope.currentNodeIndex = nodeIndex;

		search();
	};

	function search() {
		var keywords = $scope.keywords;

		if (keywords && keywords.trim()) {
			var promise = opkpService.search($scope.searchNodes[$scope.currentNodeIndex].name, keywords, ($scope.currentPage - 1) * $scope.pageSize, $scope.pageSize);
			var startTime = new Date().getTime();
			$scope.searchPromise = promise;

			promise.then(function (data) {
				if (data.total) {
					$scope.response.objects = data.objects;
					$scope.currentPage = 1;
					$scope.response.total = data.total;
				}
			});
		} else {
			$scope.response.total = 0;
		}
	}

	$scope.onEditClick = function (node, row) {
		if (node === "fir_food") {
			var modal = $uibModal.open({
				templateUrl: "templates/food-modal.html",
				controller: "food-modal",
				size: "lg",
				resolve: {
					foodId: function () {
						return row["ORIGFDCD"];
					}
				}
			});
		} else if (node === "fir_recipe") {
			var modal = $uibModal.open({
				templateUrl: "templates/recipe-modal.html",
				controller: "recipe-modal",
				size: "lg",
				resolve: {
					recipeId: function () {
						return row["RECID"];
					},
					$modal: function () {
						return $uibModal;
					}
				}
			});
		}
	};
});
