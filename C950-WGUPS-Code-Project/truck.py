
class Truck:
    def __init__(self, truck_id, package_list, truck_speed, start_time=None):
        self.truck_id = truck_id
        self.package_list = package_list
        self.truck_speed = truck_speed
        self.start_time = start_time
        self.total_distance = 0
        self.finish_time = None
