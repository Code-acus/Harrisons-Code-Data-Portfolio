
import csv
import datetime

with open('./data/distance_data.csv') as csv_file_1:
    distance_csv = list(csv.reader(csv_file_1, delimiter=','))
with open('./data/distance_name_data.csv') as csv_file_2:
    distance_name_csv = list(csv.reader(csv_file_2, delimiter=','))

    # Get package address data is in O(n) time
    def get_package_delivery_address():
        return distance_name_csv

    # Calculate the total package deliver distance from the row/column values which is in O(1) time
    def get_package_delivery_distance(row, col, total):
        distance = distance_csv[row][col]
        if distance == '':
            distance = distance_csv[col][row]

        return total + float(distance)

    # Calculate the current package delivery distance from the row/column values which is in O(1) time
    def get_current_package_distance(row, col):
        distance = distance_csv[row][col]
        if distance == '':
            distance = distance_csv[col][row]

        return float(distance)

    # Calculate total distance for a given truck -> O(n)
    def get_delivery_time(distance, truck_list):
        new_time = distance / 18
        distance_in_minutes = '{0:02.0f}:{1:02.0f}'.format(
            *divmod(new_time * 60, 60))
        final_time = distance_in_minutes + ':00'
        truck_list.append(final_time)
        total = datetime.timedelta()
        for i in truck_list:
            (hrs, mins, secs) = i.split(':')
            total += datetime.timedelta(hours=int(hrs),
                                        minutes=int(mins), seconds=int(secs))
        return total


    first_delivery_truck = []
    first_delivery_truck_indices = []
    second_delivery_truck = []
    second_delivery_truck_indices = []
    final_delivery_truck = []
    final_delivery_truck_indices = []

    # Shortest path algo with a greedy approach
    # Because the algorithm uses a kind of recursion to determine
    # an optimal location to visit next which is also based on the current location
    # The shortest path first approach was also adapted.

    # There are 3 parameters to this code:
    # 1. Get a list of packages
    # 2. Get truck numbers
    # 3. Define and find current location of the package delivery truck

    # There are two for loops used in this code:

    # The initial for loop is used to find the shortest distance to the next location.
    # The lowest value will continually change until the code has achieved a new minimum value.

    # The second for loop determines what happens when the new lowest value has been determined.
    # In addition, conditional statements check to see which delivery truck is associated with which package.
    # Once that is achieved values are then added to the appropriate truck lists
    # with the current package taken out of the list
    # and the current location then moves to the next optimal location which is determined by the first loop.

    # Finally, a recursive call is made for the next location of the shrinking list.
    # Recursive calls will continually be made until the base case, upon which will force the
    # end the function and return empty list.

    # Base Case: Length of the list is False i.e. 0

    # Space-Time Complexity runs in O(n^2) time


    def get_shortest_path(_list, num, curr_location):
        if not len(_list):
            return _list

        find_lowest_value = 50.0
        location = 0

        for i in _list:
            value = int(i[1])
            if get_current_package_distance(curr_location, value) <= find_lowest_value:
                find_lowest_value = get_current_package_distance(
                    curr_location, value)
                location = value

        for i in _list:
            if get_current_package_distance(curr_location, int(i[1])) == find_lowest_value:
                if num == 1:
                    first_delivery_truck.append(i)
                    first_delivery_truck_indices.append(i[1])
                    _list.pop(_list.index(i))
                    curr_location = location
                    get_shortest_path(_list, 1, curr_location)
                elif num == 2:
                    second_delivery_truck.append(i)
                    second_delivery_truck_indices.append(i[1])
                    _list.pop(_list.index(i))
                    curr_location = location
                    get_shortest_path(_list, 2, curr_location)
                elif num == 3:
                    final_delivery_truck.append(i)
                    final_delivery_truck_indices.append(i[1])
                    _list.pop(_list.index(i))
                    curr_location = location
                    get_shortest_path(_list, 3, curr_location)

    # Insert 0 for the first index of each index list
    first_delivery_truck_indices.insert(0, '0')
    second_delivery_truck_indices.insert(0, '0')
    final_delivery_truck_indices.insert(0, '0')

    # The following are all helper functions to return a desired value -> O(1)
    def first_delivery_truck_index():
        return first_delivery_truck_indices

    def first_delivery_truck_list():
        return first_delivery_truck

    def second_delivery_truck_index():
        return second_delivery_truck_indices

    def second_delivery_truck_list():
        return second_delivery_truck

    def final_delivery_truck_index():
        return final_delivery_truck_indices

    def final_delivery_truck_list():
        return final_delivery_truck

