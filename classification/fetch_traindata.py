def get_data(filename):
    source = file(filename, "rb")
    data = []
    labels = []
    for l in source:
        entry = l.split("%%%")
        if len(entry) == 2:
            data.append(entry[0])
            label = int(entry[1].strip())
            labels.append(label)
    return data, labels

