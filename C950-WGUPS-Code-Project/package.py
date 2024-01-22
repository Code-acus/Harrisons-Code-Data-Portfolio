import datetime


class Package:
    def __init__(self, package_id, address, city, state, zip_code, deadline, weight, notes):
        self.package_id = package_id
        self.address = address
        self.city = city
        self.state = state
        self.zip_code = zip_code
        self.deadline = deadline
        self.weight = weight
        self.notes = notes
        self.delivery_status = "At Hub"
        self.delivery_time = None
        self.mileage = 0


    def __str__(self):
        return f'{self.package_id}\t\t\t\t {self.address}\t\t\t\t {self.city}\t\t\t\t {self.state}\t\t\t ' \
               f'{self.zip_code}\t {self.deadline}\t {self.weight}\t {self.delivery_status}\t ' \
               f'{self.delivery_time.time()}'

    def is_delivered(self):
        return self.delivery_time is not None

    def is_not_delivered(self):
        return self.delivery_time is None

    def deliver(self, delivery_time, mileage):
        self.delivery_time = delivery_time
        self.mileage = mileage

    def print_package_status_for_time(self, requested_time, start_time):
        status = "en route"

        if requested_time < start_time:
            status = "at hub"

        elif self.delivery_time is None:
            status = "en route"

        elif requested_time > self.delivery_time:
            status = "delivered"

        return f'{self.package_id}\t\t {self.address}\t\t {self.city}\t\t {self.state}\t {self.zip_code}\t ' \
               f'{self.deadline}\t {self.weight}\t {status}\t {self.delivery_time.time()}'
