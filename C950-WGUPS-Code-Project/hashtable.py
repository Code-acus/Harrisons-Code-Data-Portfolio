
class HashTable:

    def __init__(self):
        self.hashtable = [[]]
        for i in range(10):
            self.hashtable.append([])

    # Create a hash key which run in O(1)
    def get_hash_value(self, key):
        return int(key) % 10

    # Insert package into hash table which should run in O(n)
    def package_insert(self, new_package_insert):
       key_hash = self.get_hash_value(new_package_insert.package_id)
       self.hashtable[key_hash].append([new_package_insert.package_id, new_package_insert])

    # Find package in hash table
    def package_find(self, key):
        key_hash = self.get_hash_value(key)
        for p in self.hashtable[key_hash]:
            if p[0] == key:
                return p[1]
        return None

    # Get key value for package
    def get_value(self, key):
        return self.package_find(key)

    # Get the index of the address
    def __str__(self):
        bucket_num = 0
        ret_str = ""
        for bucket in self.hashtable:
            ret_str = ret_str + str(bucket_num) + "\n"
            for package in bucket:
                ret_str = ret_str + str(package)
                ret_str = ret_str + "\n"
            bucket_num += 1
        return ret_str

    # Method to get the viewable index of the address
    def __repr__(self):
        bucket_num: int = 0
        ret_str = ""
        for bucket in self.hashtable:
            ret_str = ret_str + str(bucket_num) + "\n"
            for package in bucket:
                ret_str = ret_str + str(package)
                ret_str = ret_str + "\n"
            bucket_num += 1
        return ret_str
